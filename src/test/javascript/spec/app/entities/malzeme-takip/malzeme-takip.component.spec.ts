/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeTakipComponent } from 'app/entities/malzeme-takip/malzeme-takip.component';
import { MalzemeTakipService } from 'app/entities/malzeme-takip/malzeme-takip.service';
import { MalzemeTakip } from 'app/shared/model/malzeme-takip.model';

describe('Component Tests', () => {
    describe('MalzemeTakip Management Component', () => {
        let comp: MalzemeTakipComponent;
        let fixture: ComponentFixture<MalzemeTakipComponent>;
        let service: MalzemeTakipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeTakipComponent],
                providers: []
            })
                .overrideTemplate(MalzemeTakipComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MalzemeTakipComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeTakipService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MalzemeTakip(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.malzemeTakips[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
