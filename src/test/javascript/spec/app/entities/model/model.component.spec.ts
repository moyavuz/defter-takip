/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { ModelComponent } from 'app/entities/model/model.component';
import { ModelService } from 'app/entities/model/model.service';
import { Model } from 'app/shared/model/model.model';

describe('Component Tests', () => {
    describe('Model Management Component', () => {
        let comp: ModelComponent;
        let fixture: ComponentFixture<ModelComponent>;
        let service: ModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ModelComponent],
                providers: []
            })
                .overrideTemplate(ModelComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ModelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModelService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Model(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.models[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
