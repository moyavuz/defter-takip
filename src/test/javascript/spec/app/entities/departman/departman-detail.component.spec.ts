/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DepartmanDetailComponent } from 'app/entities/departman/departman-detail.component';
import { Departman } from 'app/shared/model/departman.model';

describe('Component Tests', () => {
    describe('Departman Management Detail Component', () => {
        let comp: DepartmanDetailComponent;
        let fixture: ComponentFixture<DepartmanDetailComponent>;
        const route = ({ data: of({ departman: new Departman(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DepartmanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepartmanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepartmanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.departman).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
